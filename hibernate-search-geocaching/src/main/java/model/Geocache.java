package model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.lucene.analysis.charfilter.MappingCharFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Spatial;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * @author Jan Martiska
 */
@Entity
@Indexed
@Spatial
public class Geocache {

    @Id
    private String gcCode;

    @Field(store = Store.YES)
  /*  @AnalyzerDef(name = "xx",
            tokenizer = @TokenizerDef(
                    factory = StandardTokenizerFactory.class
            ),
            charFilters = {
                    @CharFilterDef(factory = MappingCharFilterFactory.class, params = {
                            @Parameter(name = "mapping", value = "mapping.txt")
                    })
            })*/                            // TODO: make this work somehow?
    private String name;

    @Latitude
    private Double latitude;

    @Longitude
    private Double longitude;

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
                '}';
    }
}
