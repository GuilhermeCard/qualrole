CREATE TABLE queue (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  present_people BIGINT NOT NULL,
  max_capacity BIGINT NOT NULL,
  company_id BIGINT NOT NULL,
  dat_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dat_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_queue_company FOREIGN KEY (company_id) REFERENCES company(id)
);

CREATE TABLE queue_audit (
  id BIGINT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT NOT NULL,
  present_people BIGINT NOT NULL,
  max_capacity BIGINT NOT NULL,
  company_id BIGINT NOT NULL,

  CONSTRAINT fk_revinfo_queue_audit FOREIGN KEY (rev) REFERENCES revinfo(rev)
);