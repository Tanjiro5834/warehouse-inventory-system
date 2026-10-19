package com.warehouse.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {

    private static final Path DATA_DIR =
            Path.of(System.getProperty("user.home"), ".warehouse-inventory");
    private static final String URL =
            "jdbc:sqlite:" + DATA_DIR.resolve("warehouse.db");

    private Database() {}

    /** Call once at startup, before any repository is used. */
    public static void init() {
        try {
            Files.createDirectories(DATA_DIR);
            runSchema();
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot create data folder", e);
        }
    }

    /** One connection per operation. Always use in try-with-resources. */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");   // OFF by default in SQLite
        }
        return conn;
    }

    private static void runSchema() {
        try (InputStream in = Database.class.getResourceAsStream("/db/schema.sql")) {
            if (in == null) throw new IllegalStateException("db/schema.sql not found on classpath");
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
                for (String stmt : sql.split(";")) {
                    if (!stmt.isBlank()) st.execute(stmt);
                }
            }
        } catch (IOException | SQLException e) {
            throw new IllegalStateException("Failed to initialize database", e);
        }
    }
}