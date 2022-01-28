DROP TABLE student IF EXISTS;
DROP TABLE scheduled_assessment IF EXISTS;

CREATE TABLE student (
    student_id INTEGER PRIMARY KEY
);
CREATE INDEX ON student(student_id);

CREATE SEQUENCE scheduled_assessment_id_sequence START WITH 1;
CREATE TABLE scheduled_assessment (
                                      scheduled_assessment_id INTEGER DEFAULT NEXT VALUE FOR scheduled_assessment_id_sequence PRIMARY KEY ,
                                      assessment_id INTEGER NOT NULL,
                                      student_id INTEGER,
                                      unit_code VARCHAR(30) NOT NULL,
                                      assessment_weight REAL NOT NULL,
                                      start_date DATE,
                                      due_date DATE NOT NULL
);
ALTER TABLE scheduled_assessment ADD CONSTRAINT fk_student_id_scheduled_assessment FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE scheduled_assessment ADD CONSTRAINT unique_scheduled_assessment UNIQUE (assessment_id, student_id);
CREATE INDEX ON student(student_id);