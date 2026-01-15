package software.ulpgc.kata6.architecture.viewmodel;

import software.ulpgc.kata6.architecture.model.Movie;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {
    private final Stream<Movie> movies;
    private final Map<String, String> labels;


    public static HistogramBuilder with(Stream<Movie> movies) {
        if (movies == null) throw new IllegalArgumentException("movies is null");
        return new HistogramBuilder(movies);
    }

    private HistogramBuilder(Stream<Movie> movies){
        this.movies = movies
                .filter(m->m.year() >= 1900)
                .filter(m->m.year() <= 2025);
        this.labels = new HashMap<>();
    }

    public HistogramBuilder title(String label){
        labels.put("title", label);
        return this;
    }
    public HistogramBuilder x(String label){
        labels.put("x", label);
        return this;
    }

    public HistogramBuilder legend(String label){
        labels.put("legend", label);
        return this;
    }
    public Histogram build(Function<Movie, Integer> binarize){
        Histogram histogram = new Histogram(labels);
        movies.map(binarize).forEach(histogram::add);
        return histogram;
    }

}
