INSERT INTO Wallet (Id, balance)
VALUES
('f332bdaf-4b69-4e78-b775-41e4ef01e92a', 41000.86),
('35b298f3-fafb-462f-a4a2-4b1f566ee71f', 45613.23),
('e4c3a639-8ea8-4146-8a75-d10e54f5cfda', 753.12),
('2fa538aa-e095-41f3-ac8a-4781e89eae5c', 387.23)
ON CONFLICT (id) DO NOTHING;