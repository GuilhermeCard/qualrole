CREATE TABLE company (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  document VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  address_id BIGINT NOT NULL,
  phone VARCHAR(40) NOT NULL,
  social_network TEXT,
  address_number VARCHAR(80) NOT NULL,
  address_complement VARCHAR(100),
  dat_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dat_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_company_address FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE company_audit (
  id BIGINT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT NOT NULL,
  document VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  address_id BIGINT NOT NULL,
  phone VARCHAR(40) NOT NULL,
  social_network TEXT,
  address_number VARCHAR(80) NOT NULL,
  address_complement VARCHAR(100),
  dat_creation TIMESTAMP NOT NULL,
  dat_update TIMESTAMP NOT NULL,

  CONSTRAINT fk_revinfo_company_audit FOREIGN KEY (rev) REFERENCES revinfo(rev)
);