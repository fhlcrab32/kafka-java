/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.learn.kafka.model;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

import java.io.Serial;

@org.apache.avro.specific.AvroGenerated
public class Album extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  @Serial
  private static final long serialVersionUID = 6786717673599598680L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Album\",\"namespace\":\"com.learn.kafka.model\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"year\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Album> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Album> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Album> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Album> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Album> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Album to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Album from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Album instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Album fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String name;
  private int year;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Album() {}

  /**
   * All-args constructor.
   * @param name The new value for name
   * @param year The new value for year
   */
  public Album(java.lang.String name, java.lang.Integer year) {
    this.name = name;
    this.year = year;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return name;
    case 1: return year;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: name = value$ != null ? value$.toString() : null; break;
    case 1: year = (java.lang.Integer)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.String getName() {
    return name;
  }



  /**
   * Gets the value of the 'year' field.
   * @return The value of the 'year' field.
   */
  public int getYear() {
    return year;
  }



  /**
   * Creates a new Album RecordBuilder.
   * @return A new Album RecordBuilder
   */
  public static com.learn.kafka.model.Album.Builder newBuilder() {
    return new com.learn.kafka.model.Album.Builder();
  }

  /**
   * Creates a new Album RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Album RecordBuilder
   */
  public static com.learn.kafka.model.Album.Builder newBuilder(com.learn.kafka.model.Album.Builder other) {
    if (other == null) {
      return new com.learn.kafka.model.Album.Builder();
    } else {
      return new com.learn.kafka.model.Album.Builder(other);
    }
  }

  /**
   * Creates a new Album RecordBuilder by copying an existing Album instance.
   * @param other The existing instance to copy.
   * @return A new Album RecordBuilder
   */
  public static com.learn.kafka.model.Album.Builder newBuilder(com.learn.kafka.model.Album other) {
    if (other == null) {
      return new com.learn.kafka.model.Album.Builder();
    } else {
      return new com.learn.kafka.model.Album.Builder(other);
    }
  }

  /**
   * RecordBuilder for Album instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Album>
    implements org.apache.avro.data.RecordBuilder<Album> {

    private java.lang.String name;
    private int year;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.learn.kafka.model.Album.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.year)) {
        this.year = data().deepCopy(fields()[1].schema(), other.year);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing Album instance
     * @param other The existing instance to copy.
     */
    private Builder(com.learn.kafka.model.Album other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.year)) {
        this.year = data().deepCopy(fields()[1].schema(), other.year);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.String getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public com.learn.kafka.model.Album.Builder setName(java.lang.String value) {
      validate(fields()[0], value);
      this.name = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public com.learn.kafka.model.Album.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'year' field.
      * @return The value.
      */
    public int getYear() {
      return year;
    }


    /**
      * Sets the value of the 'year' field.
      * @param value The value of 'year'.
      * @return This builder.
      */
    public com.learn.kafka.model.Album.Builder setYear(int value) {
      validate(fields()[1], value);
      this.year = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'year' field has been set.
      * @return True if the 'year' field has been set, false otherwise.
      */
    public boolean hasYear() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'year' field.
      * @return This builder.
      */
    public com.learn.kafka.model.Album.Builder clearYear() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public Album build() {
      try {
        Album record = new Album();
        record.name = fieldSetFlags()[0] ? this.name : (java.lang.String) defaultValue(fields()[0]);
        record.year = fieldSetFlags()[1] ? this.year : (java.lang.Integer) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Album>
    WRITER$ = (org.apache.avro.io.DatumWriter<Album>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Album>
    READER$ = (org.apache.avro.io.DatumReader<Album>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.name);

    out.writeInt(this.year);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.name = in.readString();

      this.year = in.readInt();

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.name = in.readString();
          break;

        case 1:
          this.year = in.readInt();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}









