
DROP TABLE IF EXISTS "movie";

CREATE TABLE "movie" (
  "id" BIGINT NOT NULL,
  "title" VARCHAR(150),
  "descp" VARCHAR(1000),
  "runtime" INT,
  "language" VARCHAR(25),
  "rls_dt" TIMESTAMP,
  PRIMARY KEY ("id")
);
