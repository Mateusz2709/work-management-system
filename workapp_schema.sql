-- =========================================================
-- DEMO DATABASE SEED: employees, orders, and work logs (3 days)
-- =========================================================
USE workapp;

-- ---------- CLEAN ----------
DELETE FROM work_logs;
DELETE FROM orders;
DELETE FROM employees;

-- =========================================================
-- EMPLOYEES
-- =========================================================
INSERT INTO employees (first_name, last_name, target)
VALUES
  ('John', 'Smith', 500),
  ('Anna', 'Kowalska', 420),
  ('Mark', 'Taylor', 480),
  ('Sophie', 'Brown', 450),
  ('Liam', 'Nowak', 400);

-- =========================================================
-- ORDERS
-- expected_minutes = minutes per unit
-- =========================================================
INSERT INTO orders (order_number, order_name, expected_minutes, total_quantity, status)
VALUES
  ('O-CHAIR-100',  'Chair (standard)',           100, 50, 'PENDING'),
  ('O-STOOL-050',  'Stool (basic)',               50, 80, 'PENDING'),
  ('O-WARD-200',   'Wardrobe (premium)',         200, 20, 'PENDING'),
  ('O-TABLE-120',  'Dining Table (oak)',         120, 30, 'PENDING'),
  ('O-SHELF-060',  'Wall Shelf (pine)',           60, 70, 'PENDING'),
  ('O-BED-240',    'Bed Frame (king size)',      240, 15, 'PENDING'),
  ('O-DESK-150',   'Office Desk (walnut)',       150, 25, 'PENDING'),
  ('O-DOOR-180',   'Interior Door (painted)',    180, 40, 'PENDING'),
  ('O-CAB-130',    'Cabinet (white finish)',     130, 50, 'PENDING'),
  ('O-BENCH-090',  'Bench (garden)',              90, 40, 'PENDING'),
  ('O-SMALL-040',  'Small Drawer Unit',           40, 100, 'PENDING');

-- =========================================================
-- VARIABLES
-- =========================================================
SET @JOHN  = (SELECT id FROM employees WHERE first_name='John' LIMIT 1);
SET @ANNA  = (SELECT id FROM employees WHERE first_name='Anna' LIMIT 1);
SET @MARK  = (SELECT id FROM employees WHERE first_name='Mark' LIMIT 1);
SET @SOPH  = (SELECT id FROM employees WHERE first_name='Sophie' LIMIT 1);
SET @LIAM  = (SELECT id FROM employees WHERE first_name='Liam' LIMIT 1);

SET @CHAIR = (SELECT id FROM orders WHERE order_number='O-CHAIR-100' LIMIT 1);
SET @STOOL = (SELECT id FROM orders WHERE order_number='O-STOOL-050' LIMIT 1);
SET @WARD  = (SELECT id FROM orders WHERE order_number='O-WARD-200'  LIMIT 1);
SET @TABLE = (SELECT id FROM orders WHERE order_number='O-TABLE-120' LIMIT 1);
SET @SHELF = (SELECT id FROM orders WHERE order_number='O-SHELF-060' LIMIT 1);
SET @BED   = (SELECT id FROM orders WHERE order_number='O-BED-240'   LIMIT 1);
SET @DESK  = (SELECT id FROM orders WHERE order_number='O-DESK-150'  LIMIT 1);
SET @DOOR  = (SELECT id FROM orders WHERE order_number='O-DOOR-180'  LIMIT 1);
SET @CAB   = (SELECT id FROM orders WHERE order_number='O-CAB-130'   LIMIT 1);
SET @BENCH = (SELECT id FROM orders WHERE order_number='O-BENCH-090' LIMIT 1);
SET @SMALL = (SELECT id FROM orders WHERE order_number='O-SMALL-040' LIMIT 1);

-- =========================================================
-- DAY 1: 2025-10-30
-- =========================================================
-- John (target 500): chairs + stools + desk = 550
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@JOHN, @CHAIR, '2025-10-30'), (@JOHN, @CHAIR, '2025-10-30'),
  (@JOHN, @STOOL, '2025-10-30'), (@JOHN, @STOOL, '2025-10-30'),
  (@JOHN, @DESK, '2025-10-30');

-- Anna (target 420): shelves + cabinet = 480
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@ANNA, @SHELF, '2025-10-30'), (@ANNA, @SHELF, '2025-10-30'),
  (@ANNA, @SHELF, '2025-10-30'), (@ANNA, @CAB, '2025-10-30');

-- Mark: tables + bed = 480
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@MARK, @TABLE, '2025-10-30'), (@MARK, @TABLE, '2025-10-30'),
  (@MARK, @BED,   '2025-10-30');

-- Sophie: 3 benches + 1 door = 450
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@SOPH, @BENCH, '2025-10-30'), (@SOPH, @BENCH, '2025-10-30'),
  (@SOPH, @BENCH, '2025-10-30'), (@SOPH, @DOOR,  '2025-10-30');

-- Liam: 5 small drawers (200) + 2 stools (100) + 1 table (120) = 420
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@LIAM, @SMALL, '2025-10-30'), (@LIAM, @SMALL, '2025-10-30'),
  (@LIAM, @SMALL, '2025-10-30'), (@LIAM, @SMALL, '2025-10-30'),
  (@LIAM, @SMALL, '2025-10-30'), (@LIAM, @STOOL, '2025-10-30'),
  (@LIAM, @STOOL, '2025-10-30'), (@LIAM, @TABLE, '2025-10-30');

-- =========================================================
-- DAY 2: 2025-10-31
-- =========================================================
-- John: chairs + wardrobes = 500
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@JOHN, @CHAIR, '2025-10-31'), (@JOHN, @CHAIR, '2025-10-31'),
  (@JOHN, @WARD,  '2025-10-31');

-- Anna: 3 stools (150) + 2 small drawers (80) + 1 chair (100) = 330
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@ANNA, @STOOL, '2025-10-31'), (@ANNA, @STOOL, '2025-10-31'),
  (@ANNA, @STOOL, '2025-10-31'), (@ANNA, @SMALL, '2025-10-31'),
  (@ANNA, @SMALL, '2025-10-31'), (@ANNA, @CHAIR, '2025-10-31');

-- Mark: 1 desk + 2 benches + 1 bed = 570
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@MARK, @DESK, '2025-10-31'),
  (@MARK, @BENCH, '2025-10-31'), (@MARK, @BENCH, '2025-10-31'),
  (@MARK, @BED,   '2025-10-31');

-- Sophie: 2 shelves (120) + 1 cabinet (130) + 1 door (180) = 430
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@SOPH, @SHELF, '2025-10-31'), (@SOPH, @SHELF, '2025-10-31'),
  (@SOPH, @CAB, '2025-10-31'), (@SOPH, @DOOR, '2025-10-31');

-- Liam: 3 small drawers (120) + 2 stools (100) + 1 desk (150) = 370
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@LIAM, @SMALL, '2025-10-31'), (@LIAM, @SMALL, '2025-10-31'),
  (@LIAM, @SMALL, '2025-10-31'), (@LIAM, @STOOL, '2025-10-31'),
  (@LIAM, @STOOL, '2025-10-31'), (@LIAM, @DESK, '2025-10-31');

-- =========================================================
-- DAY 3: 2025-11-01
-- =========================================================
-- John: 3 tables (360) + 1 chair (100) = 460
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@JOHN, @TABLE, '2025-11-01'), (@JOHN, @TABLE, '2025-11-01'),
  (@JOHN, @TABLE, '2025-11-01'), (@JOHN, @CHAIR, '2025-11-01');

-- Anna: 3 stools (150) + 2 shelves (120) + 1 cabinet (130) = 400
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@ANNA, @STOOL, '2025-11-01'), (@ANNA, @STOOL, '2025-11-01'),
  (@ANNA, @STOOL, '2025-11-01'), (@ANNA, @SHELF, '2025-11-01'),
  (@ANNA, @SHELF, '2025-11-01'), (@ANNA, @CAB, '2025-11-01');

-- Mark: 2 beds (480) = 480
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@MARK, @BED, '2025-11-01'), (@MARK, @BED, '2025-11-01');

-- Sophie: 3 benches (270) + 1 table (120) + 1 chair (100) = 490
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@SOPH, @BENCH, '2025-11-01'), (@SOPH, @BENCH, '2025-11-01'),
  (@SOPH, @BENCH, '2025-11-01'), (@SOPH, @TABLE, '2025-11-01'),
  (@SOPH, @CHAIR, '2025-11-01');

-- Liam: 2 small drawers (80) + 3 stools (150) + 1 desk (150) = 380
INSERT INTO work_logs (employee_id, order_id, work_date) VALUES
  (@LIAM, @SMALL, '2025-11-01'), (@LIAM, @SMALL, '2025-11-01'),
  (@LIAM, @STOOL, '2025-11-01'), (@LIAM, @STOOL, '2025-11-01'),
  (@LIAM, @STOOL, '2025-11-01'), (@LIAM, @DESK, '2025-11-01');
