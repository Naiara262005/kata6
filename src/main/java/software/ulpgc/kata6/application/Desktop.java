package software.ulpgc.kata6.application;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;
import software.ulpgc.kata6.architecture.viewmodel.Histogram;
import software.ulpgc.kata6.architecture.viewmodel.HistogramBuilder;

import javax.swing.*;
import java.util.stream.Stream;

public class Desktop extends JFrame {
    private final Store store;
    private Desktop(Store store){
        this.store = store;
        this.setTitle("Histogram");
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    public static Desktop with(Store store){
        return new Desktop(store);
    }
    public Desktop display(){
        display(histogramOf(movies()));
        return this;
    }
    private void display(Histogram histogram){
        this.getContentPane().add(displayOf(histogram));
        this.revalidate();
    }

    private ChartPanel displayOf(Histogram histogram) {
        return new ChartPanel(decorate(chartOf(histogram)));
    }

    private JFreeChart decorate(JFreeChart chart) {
        return chart;
    }

    private JFreeChart chartOf(Histogram histogram) {
        return ChartFactory.createHistogram(
                histogram.title(),
                histogram.x(),
                "count",
                datasetOf(histogram)
        );
    }

    private XYSeriesCollection datasetOf(Histogram histogram) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesOf(histogram));
        return dataset;
    }

    private XYSeries seriesOf(
            Histogram histogram) {
        XYSeries series = new XYSeries(histogram.legend());
        for (int bin : histogram)
            series.add(bin, histogram.count(bin));
        return series;
    }
    private Stream<Movie> movies(){
        return store.movies()
                .filter(m -> m.year() >= 1900)
                .filter(m -> m.year() <= 2025);
    }
    private Histogram histogramOf(Stream<Movie> movies){
        return HistogramBuilder.with(movies)
                .title("Peliculas por año")
                .x("Año")
                .legend("Nº películas")
                .build(Movie::year);
    }
}
