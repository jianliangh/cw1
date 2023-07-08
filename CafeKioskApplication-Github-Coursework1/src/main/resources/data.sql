INSERT INTO roles (name)
SELECT r.name FROM (
SELECT 'USER' as name 
) r
WHERE NOT EXISTS (SELECT * FROM roles);
