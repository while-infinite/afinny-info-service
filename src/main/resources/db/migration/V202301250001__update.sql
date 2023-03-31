CREATE INDEX IF NOT EXISTS bank_branch_bank_branch_type_index
    ON "info-service".bank_branch (bank_branch_type);
CREATE INDEX IF NOT EXISTS bank_branch_is_closed_index
    ON "info-service".bank_branch (is_closed);