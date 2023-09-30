ALTER TABLE medicos ADD activo TINYINT;
UPDATE medicos SET activo = 1; -- poniendo 1 a todos los registros existentes --
