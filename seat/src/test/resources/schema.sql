
DROP TABLE IF EXISTS "hall";
DROP TABLE IF EXISTS "hall_seat";

CREATE TABLE "hall" (
  "id" VARCHAR(5) NOT NULL,
  "seat_cnt" INT,
  PRIMARY KEY ("id")
);

CREATE TABLE "hall_seat" (
  "id" VARCHAR(10) NOT NULL,
  "num" INT,
  "type" VARCHAR(20),
  "hall_id" VARCHAR(5) NOT NULL,
  PRIMARY KEY ("id"),
  FOREIGN KEY ("hall_id") REFERENCES "hall"("id")
);