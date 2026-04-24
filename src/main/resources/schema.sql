DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS app_users;

CREATE TABLE app_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
);

CREATE TABLE games (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    image_url VARCHAR(255)
);

CREATE TABLE carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_users(id) ON DELETE CASCADE
);

CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INTEGER NOT NULL,
    game_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INTEGER NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES app_users(id)
);

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INTEGER NOT NULL,
    game_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES games(id)
);

-- Seed Initial Data for Games
INSERT INTO games (title, description, price, stock, image_url) VALUES 
('Cyberpunk 2077', 'An open-world, action-adventure RPG set in the dark future of Night City.', 59.99, 150, 'https://images.unsplash.com/photo-1593305841991-05c297ba4575?auto=format&fit=crop&q=80&w=800'),
('The Witcher 3: Wild Hunt', 'A story-driven, next-generation open world role-playing game.', 39.99, 200, 'https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&q=80&w=800'),
('Red Dead Redemption 2', 'Epic tale of life in America at the dawn of the modern age.', 59.99, 120, 'https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&q=80&w=800'),
('Elden Ring', 'Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring.', 59.99, 300, 'https://images.unsplash.com/photo-1552820728-8b83bb6b773f?auto=format&fit=crop&q=80&w=800'),
('God of War Ragnarök', 'Embark on an epic and heartfelt journey as Kratos and Atreus struggle with holding on and letting go.', 69.99, 80, 'https://images.unsplash.com/photo-1600861194942-f883de0dfe96?auto=format&fit=crop&q=80&w=800'),
('Hollow Knight', 'Forge your own path in Hollow Knight! An epic action adventure through a vast ruined kingdom of insects and heroes.', 14.99, 500, 'https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&q=80&w=800');
