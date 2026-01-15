package software.ulpgc.kata6.application;

import io.javalin.Javalin;
import io.javalin.http.Context;
import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:movies.db");

        DatabaseMovieStore store = DatabaseMovieStore.with(connection);

        Javalin app = Javalin.create().start(7070);

        app.get("/movies", ctx -> movies(ctx, store));
    }
        private static void movies(Context ctx, DatabaseMovieStore store){
            String yearParam = ctx.queryParam("year");
            try{
                List<String> titles = store.search(yearParam);

                if (titles.isEmpty()){
                    ctx.status(400).result("No movies found");
                    return;
                }
                ctx.result(String.join("\n", titles));
            } catch (NumberFormatException e){
                ctx.status(400).result("Invalid year");
            }
        }
    }

