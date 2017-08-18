package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.lucene.analysis.charfilter.MappingCharFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Spatial;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * @author Jan Martiska
 */
@Entity
@Indexed
@Spatial
public class Geocache {

    public static final SimpleDateFormat DATE_FORMAT_DAY_ONLY = new SimpleDateFormat("dd.MM.YYYY");

    @Id
    private String gcCode;

    @Field(store = Store.YES)
    @AnalyzerDef(name = "xx",
            tokenizer = @TokenizerDef(
                    factory = StandardTokenizerFactory.class
            ),
            filters = {
                    // this will basically make the search case-insensitive
                    @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                    // this will apply language specific stemming
                    @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                            @Parameter(name = "language", value = "English")
                    })
            })
    @Analyzer(definition = "xx")
    private String name;

    @Latitude
    private Double latitude;

    @Longitude
    private Double longitude;

    @Field(analyze = Analyze.NO, store = Store.YES)
    @DateBridge(resolution = Resolution.DAY)
    private Date placedDate;

    @Field(store = Store.YES)
    @Column(length = Integer.MAX_VALUE)
    private String longDescription;

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getGcCode() {
        return gcCode;
    }

    public void setGcCode(String gcCode) {
        this.gcCode = gcCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Geocache{" +
                "gcCode='" + gcCode + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", placedDate=" + DATE_FORMAT_DAY_ONLY.format(placedDate) +
                '}';
    }
}
