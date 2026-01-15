package software.ulpgc.kata6.application;

import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    private static final String database = "movies.db";
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database)){
            connection.setAutoCommit(false);
            Desktop.with(moviesIn(connection))
                    .display()
                    .setVisible(true);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static Store moviesIn(Connection connection) throws SQLException {
        if (isDatabaseEmpty()) importMoviesInto(connection);
        return new DatabaseStore(connection);
    }

    private static void importMoviesInto(Connection connection) throws SQLException {
        Stream<Movie> movies = new RemoteStore(MovieDeserializer::fromTsv).movies();
        new DatabaseRecorder(connection).record(movies);
        new DatabaseRecorder(connection);
    }

    private static boolean isDatabaseEmpty() {
        return new File(database).length() == 0;
    }



}