DELETE FROM Drivers WHERE 1=1;
INSERT INTO Drivers (ID, Name, Status, isDisabled)
VALUES
(7729119111, 'Sam BankmanFried', 'V', false),
(266399121, 'John Clay', 'E', false), 
(366399121, 'Julia Hicks', 'E', false),
(466399121, 'Ivan Garcia', 'E', false),
(122765234, 'Sachin Tendulkar', 'S', false), 
(9194789124, 'Charles Xavier', 'V', true),
(0, 'No Driver', 'V', false);

DELETE FROM ParkingLots WHERE 1=1;
INSERT INTO ParkingLots (LotName, Address)
VALUES
('Poulton Deck', '1021 Main Campus Dr, Raleigh, NC, 27606'),
('Partners Way Deck', '851 Partners Way, Raleigh, NC, 27606'),
('Dan Allen Parking Deck', '110 Dan Allen Dr, Raleigh, NC, 27607'),
('Foo Deck', 'Foo Dr, Raleigh, NC, 27606');

DELETE FROM Zones WHERE 1=1;
INSERT INTO Zones (ZoneID, LotName)
VALUES
('A', 'Poulton Deck'),
('B', 'Poulton Deck'),
('C', 'Poulton Deck'),
('D', 'Poulton Deck'),
('AS', 'Poulton Deck'),
('BS', 'Poulton Deck'),
('CS', 'Poulton Deck'),
('DS', 'Poulton Deck'),
('V', 'Poulton Deck'),
('A', 'Partners Way Deck'),
('B', 'Partners Way Deck'),
('C', 'Partners Way Deck'),
('D', 'Partners Way Deck'),
('AS', 'Partners Way Deck'),
('BS', 'Partners Way Deck'),
('CS', 'Partners Way Deck'),
('DS', 'Partners Way Deck'),
('V', 'Partners Way Deck'),
('A', 'Dan Allen Parking Deck'),
('B', 'Dan Allen Parking Deck'),
('C', 'Dan Allen Parking Deck'),
('D', 'Dan Allen Parking Deck'),
('AS', 'Dan Allen Parking Deck'),
('BS', 'Dan Allen Parking Deck'),
('CS', 'Dan Allen Parking Deck'),
('DS', 'Dan Allen Parking Deck'),
('V', 'Dan Allen Parking Deck'),
('F', 'Foo Deck');

DELETE FROM Spaces WHERE 1=1;
INSERT INTO Spaces (Number, SpaceType, ZoneID, LotName)
VALUES
(1, DEFAULT, 		'A',  'Poulton Deck'),
(2, DEFAULT, 		'A',  'Poulton Deck'),
(3, 'compact car', 	'A',  'Poulton Deck'),
(4, 'electric', 	'A',  'Poulton Deck'),
(5, 'handicap', 	'A',  'Poulton Deck'),
(1, DEFAULT, 		'AS', 'Poulton Deck'),
(2, DEFAULT, 		'AS', 'Poulton Deck'),
(3, 'compact car', 	'AS', 'Poulton Deck'),
(4, 'electric', 	'AS', 'Poulton Deck'),
(5, 'handicap', 	'AS', 'Poulton Deck'),
(1, DEFAULT, 		'V',  'Poulton Deck'),
(2, DEFAULT, 		'V',  'Poulton Deck'),
(3, 'compact car', 	'V',  'Poulton Deck'),
(4, 'electric', 	'V',  'Poulton Deck'),
(5, 'handicap', 	'V',  'Poulton Deck'),
(1, DEFAULT, 		'A',  'Partners Way Deck'),
(2, DEFAULT, 		'A',  'Partners Way Deck'),
(3, 'compact car', 	'A',  'Partners Way Deck'),
(4, 'electric', 	'A',  'Partners Way Deck'),
(5, 'handicap', 	'A',  'Partners Way Deck'),
(1, DEFAULT, 		'AS', 'Partners Way Deck'),
(2, DEFAULT, 		'AS', 'Partners Way Deck'),
(3, 'compact car', 	'AS', 'Partners Way Deck'),
(4, 'electric', 	'AS', 'Partners Way Deck'),
(5, 'handicap', 	'AS', 'Partners Way Deck'),
(1, DEFAULT, 		'V',  'Partners Way Deck'),
(2, DEFAULT, 		'V',  'Partners Way Deck'),
(3, 'compact car', 	'V',  'Partners Way Deck'),
(4, 'electric', 	'V',  'Partners Way Deck'),
(5, 'handicap', 	'V',  'Partners Way Deck'),
(1, DEFAULT, 		'A',  'Dan Allen Parking Deck'),
(2, DEFAULT, 		'A',  'Dan Allen Parking Deck'),
(3, 'compact car', 	'A',  'Dan Allen Parking Deck'),
(4, 'electric', 	'A',  'Dan Allen Parking Deck'),
(5, 'handicap', 	'A',  'Dan Allen Parking Deck'),
(1, DEFAULT, 		'AS', 'Dan Allen Parking Deck'),
(2, DEFAULT, 		'AS', 'Dan Allen Parking Deck'),
(3, 'compact car', 	'AS', 'Dan Allen Parking Deck'),
(4, 'electric', 	'AS', 'Dan Allen Parking Deck'),
(5, 'handicap', 	'AS', 'Dan Allen Parking Deck'),
(1, DEFAULT, 		'V',  'Dan Allen Parking Deck'),
(2, DEFAULT, 		'V',  'Dan Allen Parking Deck'),
(3, 'compact car', 	'V',  'Dan Allen Parking Deck'),
(4, 'electric', 	'V',  'Dan Allen Parking Deck'),
(5, 'handicap', 	'V',  'Dan Allen Parking Deck'),
(0, DEFAULT,		'F',  'Foo Deck');

DELETE FROM Permits WHERE 1=1;
INSERT INTO Permits (PermitID, ID, ZoneID, LotName, SpaceType, PermitType, StartDate, ExpDate, ExpTime)
VALUES
('VSBF1C', 7729119111, 'V', 'Poulton Deck', 'regular', 'commuter', '2023-01-01', '2024-01-01', '06:00:00'),
('EJC1R', 266399121, 'A', 'Poulton Deck', 'electric', 'residential', '2010-01-01', '2030-01-01', '06:00:00'),
('EJH2C', 366399121, 'A', 'Poulton Deck', 'regular', 'commuter', '2023-01-01', '2024-01-01', '06:00:00'),
('EIG3C', 466399121, 'A', 'Poulton Deck', 'regular', 'commuter', '2023-01-01', '2024-01-01', '06:00:00'),
('SST1R', 122765234, 'AS', 'Poulton Deck', 'compact car', 'residential', '2022-01-01', '2023-09-30', '06:00:00'),
('VCX1SE', 9194789124, 'V', 'Poulton Deck', 'handicap', 'special event', '2023-01-01', '2023-11-15', '06:00:00'),
('No Permit', 0, 'F', 'Foo Deck', DEFAULT, 'commuter', '2023-01-01', '2053-01-01', '06:00:00');

DELETE FROM ModelInfo WHERE 1=1;
INSERT INTO ModelInfo (Model, Manufacturer)
VALUES
('GT-R-Nismo', 'Nissan'),
('Model S', 'Tesla'),
('M2 Coupe', 'BMW'),
('Continental GT Speed', 'Bentley'),
('Civic SI', 'Honda'),
('Taycan Sport Turismo', 'Porsche'),
('Macan GTS', NULL);

DELETE FROM Vehicles WHERE 1=1;
INSERT INTO Vehicles (Plate, ID, PermitID, Year, Color, Model)
VALUES
('SBF', 7729119111, 'VSBF1C', 2024, 'Pearl White TriCoat', 'GT-R-Nismo'),
('Clay1', 266399121, 'EJC1R', 2023, 'Ultra Red', 'Model S'),
('Hicks1', 366399121, 'EJH2C', 2024, 'Zandvoort Blue', 'M2 Coupe'),
('Garcia1', 466399121, 'EIG3C', 2024, 'Blue Fusion', 'Continental GT Speed'), 
('CRICKET', 122765234, 'SST1R', 2024, 'Sonic Gray Pearl', 'Civic SI'),
('PROFX', 9194789124, 'VCX1SE', 2024, 'Frozenblue Metallic', 'Taycan Sport Turismo'),
('VAN-9910', 0, 'No Permit', NULL, 'Papaya Metallic', 'Macan GTS'); 

DELETE FROM Citations WHERE 1=1;
INSERT INTO Citations (CitationID, Plate, Number, ZoneID, LotName, PayStatus, Category, Fee, CitationDate, CitationTime)
VALUES
('NP1', 'VAN-9910', 1, 'A', 'Dan Allen Parking Deck', 'paid', 'No Permit', 40, '2021-10-11', '08:00:00'),
('EP1', 'CRICKET', 3, 'AS', 'Poulton Deck', 'unpaid', 'Expired Permit', 30, '2023-10-01', '08:00:00');

DELETE FROM ParkingActivity WHERE 1=1;
