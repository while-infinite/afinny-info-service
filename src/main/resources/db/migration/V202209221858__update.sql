ALTER TABLE error_message DROP COLUMN if exists attachment_id;
ALTER TABLE error_message ALTER COLUMN user_email TYPE VARCHAR(50);

