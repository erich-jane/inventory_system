import sqlite3
import hashlib
from pathlib import Path

DB_PATH = Path(__file__).resolve().parent.parent / 'inventory.db'

conn = sqlite3.connect(str(DB_PATH))
cur = conn.cursor()

cur.execute('''CREATE TABLE IF NOT EXISTS users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    role TEXT NOT NULL
)''')

cur.execute('''CREATE TABLE IF NOT EXISTS items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    quantity INTEGER,
    price REAL
)''')

def h(s):
    return hashlib.sha256(s.encode('utf-8')).hexdigest()

# Seed users
users = [
    ('admin', h('admin'), 'admin'),
    ('erich', h('erich123'), 'admin'),
    ('user1', h('password1'), 'user')
]
for u,p,r in users:
    try:
        cur.execute('INSERT INTO users(username,password,role) VALUES(?,?,?)', (u,p,r))
    except sqlite3.IntegrityError:
        pass

# Seed items
items = [
    ('Paper A4', 100, 2.50),
    ('Ballpoint Pen', 200, 0.99),
    ('Stapler', 20, 5.75)
]
for name,qty,price in items:
    cur.execute('SELECT COUNT(*) FROM items WHERE name = ?', (name,))
    if cur.fetchone()[0] == 0:
        cur.execute('INSERT INTO items(name,quantity,price) VALUES(?,?,?)', (name,qty,price))

conn.commit()
print('Created database at:', DB_PATH)
# Print summary
cur.execute('SELECT COUNT(*) FROM users')
print('Users:', cur.fetchone()[0])
cur.execute('SELECT COUNT(*) FROM items')
print('Items:', cur.fetchone()[0])
conn.close()