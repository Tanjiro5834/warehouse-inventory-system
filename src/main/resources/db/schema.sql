CREATE TABLE IF NOT EXISTS users (
    id            TEXT PRIMARY KEY,
    username      TEXT NOT NULL UNIQUE COLLATE NOCASE,
    salt          TEXT NOT NULL,
    password_hash TEXT NOT NULL,
    display_name  TEXT,
    role          TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS units (
    id           TEXT PRIMARY KEY,
    brand        TEXT NOT NULL,
    model        TEXT NOT NULL,
    capacity_hp  REAL NOT NULL,
    type         TEXT NOT NULL,
    serial       TEXT NOT NULL UNIQUE,
    stock_status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS consumables (
    id            TEXT PRIMARY KEY,
    name          TEXT NOT NULL UNIQUE COLLATE NOCASE,
    quantity      INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    unit          TEXT NOT NULL,
    reorder_level INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS tools (
    id                     TEXT PRIMARY KEY,
    name                   TEXT NOT NULL,
    quantity               INTEGER NOT NULL DEFAULT 0,
    status                 TEXT NOT NULL,
    assigned_technician_id TEXT REFERENCES technicians(id)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id             TEXT PRIMARY KEY,
    name           TEXT NOT NULL UNIQUE COLLATE NOCASE,
    contact_person TEXT,
    phone          TEXT,
    email          TEXT,
    address        TEXT
);

CREATE TABLE IF NOT EXISTS technicians (
    id        TEXT PRIMARY KEY,
    name      TEXT NOT NULL,
    specialty TEXT,
    phone     TEXT
);

CREATE TABLE IF NOT EXISTS stock_in (
    id          TEXT PRIMARY KEY,
    date        TEXT NOT NULL,
    supplier    TEXT NOT NULL,
    dr_number   TEXT NOT NULL UNIQUE,
    received_by TEXT NOT NULL,
    remarks     TEXT
);

CREATE TABLE IF NOT EXISTS stock_in_lines (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    stock_in_id TEXT NOT NULL REFERENCES stock_in(id) ON DELETE CASCADE,
    item_id     TEXT NOT NULL,
    quantity    INTEGER NOT NULL CHECK (quantity > 0)
);

CREATE TABLE IF NOT EXISTS stock_in_line_serials (
    line_id INTEGER NOT NULL REFERENCES stock_in_lines(id) ON DELETE CASCADE,
    serial  TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS stock_out (
    id            TEXT PRIMARY KEY,
    date          TEXT NOT NULL,
    issued_to     TEXT NOT NULL,
    job_reference TEXT NOT NULL,
    released_by   TEXT NOT NULL,
    remarks       TEXT
);

CREATE TABLE IF NOT EXISTS stock_out_lines (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    stock_out_id TEXT NOT NULL REFERENCES stock_out(id) ON DELETE CASCADE,
    item_id      TEXT NOT NULL,
    quantity     INTEGER NOT NULL CHECK (quantity > 0)
);

CREATE TABLE IF NOT EXISTS stock_out_line_serials (
    line_id INTEGER NOT NULL REFERENCES stock_out_lines(id) ON DELETE CASCADE,
    serial  TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tool_checkouts (
    id            TEXT PRIMARY KEY,
    tool_id       TEXT NOT NULL REFERENCES tools(id),
    tool_name     TEXT,
    holder_id     TEXT NOT NULL REFERENCES technicians(id),
    holder_name   TEXT,
    date_out      TEXT NOT NULL,
    date_returned TEXT,
    status        TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    id        TEXT PRIMARY KEY,
    date      TEXT NOT NULL,
    type      TEXT NOT NULL,
    item_id   TEXT NOT NULL,
    item_name TEXT,
    quantity  INTEGER NOT NULL,
    reference TEXT,
    user      TEXT
);

CREATE INDEX IF NOT EXISTS idx_tx_date ON transactions(date);
CREATE INDEX IF NOT EXISTS idx_tx_item ON transactions(item_id)