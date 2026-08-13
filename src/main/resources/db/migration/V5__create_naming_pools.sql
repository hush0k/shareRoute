CREATE SCHEMA IF NOT EXISTS reference;

CREATE TABLE reference.first_name_translation (
                                                  id SERIAL PRIMARY KEY,
                                                  first_name_group_id INTEGER NOT NULL,
                                                  language_code VARCHAR(5) NOT NULL,
                                                  value VARCHAR(64) NOT NULL,
                                                  UNIQUE (first_name_group_id, language_code)
);


CREATE TABLE reference.last_name_translation (
                                                  id SERIAL PRIMARY KEY,
                                                  last_name_group_id INTEGER NOT NULL,
                                                  language_code VARCHAR(5) NOT NULL,
                                                  value VARCHAR(64) NOT NULL,
                                                  UNIQUE (last_name_group_id, language_code)
);