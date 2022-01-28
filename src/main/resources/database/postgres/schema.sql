DROP SCHEMA public cascade;
CREATE SCHEMA public;
CREATE TABLE student (
                      student_id INT PRIMARY KEY
);
CREATE INDEX ON student(student_id);

CREATE SEQUENCE scheduled_assessment_id_sequence INCREMENT BY 1;
CREATE TABLE scheduled_assessment (
                         scheduled_assessment_id INT PRIMARY KEY DEFAULT nextval('scheduled_assessment_id_sequence'),
                         assessment_id INT NOT NULL,
                         student_id INT references student(student_id),
                         unit_code TEXT NOT NULL,
                         assessment_weight REAL NOT NULL,
                         start_date DATE,
                         due_date DATE NOT NULL,
                         CONSTRAINT unique_scheduled_assessment UNIQUE (assessment_id, student_id)
);
ALTER SEQUENCE scheduled_assessment_id_sequence OWNED BY student.student_id;
CREATE INDEX ON student(student_id);
