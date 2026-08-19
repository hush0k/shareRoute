ALTER TABLE gamefication.archetype
    DROP COLUMN description;

ALTER TABLE gamefication.archetype
    ADD COLUMN archetype_state TEXT NOT NULL DEFAULT 'DRAFT'
        CONSTRAINT chk_archetype_state CHECK (archetype_state IN ('DRAFT', 'PUBLISHED', 'CANCELED'));

ALTER TABLE gamefication.archetype_translation
    ADD COLUMN description TEXT;

ALTER TABLE gamefication.user_archetype
    ADD COLUMN model_version TEXT,
    ADD COLUMN confidence_score DOUBLE PRECISION;