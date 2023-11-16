SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Citations;
DROP TABLE IF EXISTS Drivers;
DROP TABLE IF EXISTS ModelInfo;
DROP TABLE IF EXISTS ParkingActivity;
DROP TABLE IF EXISTS ParkingLots;
DROP TABLE IF EXISTS Permits;
DROP TABLE IF EXISTS Spaces;
DROP TABLE IF EXISTS Vehicles;
DROP TABLE IF EXISTS Zones;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Drivers (
	ID VARCHAR(10),
	Name VARCHAR(128) NOT NULL,
	Status ENUM('S', 'E', 'V') NOT NULL,
	isDisabled BOOLEAN NOT NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE ParkingLots (
	LotName VARCHAR(128),
	Address VARCHAR(256) NOT NULL,
	PRIMARY KEY(LotName)
);

CREATE TABLE Zones(
	ZoneID ENUM ('A', 'B', 'C', 'D', 'AS', 'BS', 'CS', 'DS', 'V', 'F'),
	LotName VARCHAR(128) NOT NULL,
	PRIMARY KEY(ZoneID, LotName),
	FOREIGN KEY(LotName) 
		REFERENCES ParkingLots(LotName) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Spaces(
	Number INT,
	SpaceType ENUM('electric', 'handicap', 'compact car', 'regular') NOT NULL DEFAULT 'regular',
	ZoneID ENUM ('A', 'B', 'C', 'D', 'AS', 'BS', 'CS', 'DS', 'V', 'F') NOT NULL, 
	LotName VARCHAR (128) NOT NULL,
	PRIMARY KEY(Number, ZoneID, LotName),
	FOREIGN KEY (ZoneID, LotName) 
		REFERENCES Zones(ZoneID, LotName) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Permits(
	PermitID VARCHAR(10),
	ID VARCHAR(10) NOT NULL,
	ZoneID ENUM ('A', 'B', 'C', 'D', 'AS', 'BS', 'CS', 'DS', 'V', 'F') NOT NULL, 
	LotName VARCHAR (128) NOT NULL,
	SpaceType ENUM('electric', 'handicap', 'compact car', 'regular') NOT NULL DEFAULT 'regular',
	PermitType ENUM('residential', 'commuter', 'peak hours', 'special event','park & ride') NOT NULL,
	StartDate DATE NOT NULL,
	ExpDate DATE NOT NULL,
	ExpTime TIME NOT NULL,
	PRIMARY KEY(PermitID),
	FOREIGN KEY (ID) 
		REFERENCES Drivers(ID) 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (ZoneID, LotName) 
		REFERENCES Zones(ZoneID, LotName) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ModelInfo(
	Model VARCHAR(128),
	Manufacturer VARCHAR(256),
	PRIMARY KEY (Model)
);

CREATE TABLE Vehicles(
	Plate VARCHAR(20),
	ID VARCHAR(10) NOT NULL,
	PermitID VARCHAR(10) NOT NULL,
	Year YEAR,
	Color VARCHAR(20),
	Model VARCHAR(128),
	PRIMARY KEY (Plate),
	FOREIGN KEY (Model) 
		REFERENCES ModelInfo(Model) 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (ID)
		REFERENCES Drivers(ID)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (PermitID)
		REFERENCES Permits(PermitID)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Citations(
	CitationID VARCHAR(10),
	Plate VARCHAR(20) NOT NULL,
	Number INT NOT NULL,
	ZoneID ENUM ('A', 'B', 'C', 'D', 'AS', 'BS', 'CS', 'DS', 'V', 'F') NOT NULL,
	LotName VARCHAR(128) NOT NULL,
	PayStatus ENUM('unpaid', 'paid', 'appealed', 'invalid') DEFAULT 'unpaid' NOT NULL,
	Category ENUM('Invalid Permit', 'Expired Permit', 'No Permit') DEFAULT 'Invalid Permit' NOT NULL,
	Fee FLOAT DEFAULT 25 NOT NULL,
	CitationDate DATE NOT NULL,
	CitationTime TIME NOT NULL,
	PRIMARY KEY(CitationID),
	FOREIGN KEY (Plate) 
		REFERENCES Vehicles(Plate) 
		ON UPDATE CASCADE,
	FOREIGN KEY (Number, ZoneID, LotName) 
		REFERENCES Spaces(Number, ZoneID, LotName) 
		ON UPDATE CASCADE
);

CREATE TABLE ParkingActivity(
	Plate VARCHAR(20),
	Timestamp DATETIME NOT NULL,
	Number INT NOT NULL,
	ZoneID ENUM ('A', 'B', 'C', 'D', 'AS', 'BS', 'CS', 'DS', 'V', 'F') NOT NULL,
	LotName VARCHAR(128) NOT NULL,
	LastAction ENUM('parking','exiting') NOT NULL,
	PRIMARY KEY (Plate, Timestamp),
	FOREIGN KEY (Plate) 
		REFERENCES Vehicles(Plate) 
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (Number, ZoneID, LotName) 
		REFERENCES Spaces(Number, ZoneID, LotName)
		ON DELETE CASCADE ON UPDATE CASCADE
);
