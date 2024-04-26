CREATE TABLE queue (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  present_people BIGINT NOT NULL,
  max_capacity BIGINT NOT NULL,
  company_id BIGINT NOT NULL,
  dat_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dat_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_queue_company FOREIGN KEY (company_id) REFERENCES company(id)
);
