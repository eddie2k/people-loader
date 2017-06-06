package com.example.peopleloader.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.peopleloader.model.BirthDate;
import com.example.peopleloader.model.Name;
import com.example.peopleloader.model.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public final class JsonFileLoader implements Loader {

	@Override
	public Stream<Person> loadFromFile(String filename) throws FileNotFoundException {
		Objects.requireNonNull(filename);

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory jsonFactory = new JsonFactory();
			JsonParser jsonParser = jsonFactory.createParser(new File(filename));
			// We might have memory troubles here for huge json files. Consider jackson streaming parser.
			// It adds complication, may be slower but - no such limits.
			List<JsonPerson> loadedJsonPerson = Arrays.asList(mapper.readValue(jsonParser, JsonPerson[].class));
			return loadedJsonPerson.stream()
					// using parallel stream might help to achieve better performance, but only for non-streaming parser
					// Let's not optimize too much ;)
					.map(p -> new Person(p.name, p.birthDate)).collect(Collectors.toList()) 
					
					.stream();
		} catch (JsonParseException | JsonMappingException e) {
			throw new com.example.peopleloader.loader.exception.JsonParseException(e);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Internal class to avoid polluting core classes with Jackson annotations
	 */
	// It is hard to answer is it a good or bad idea. I personally annotate core classes. 
	// Argument: image adding new field to Person. Ideally it should require only one change - of Person class.
	private static class JsonPerson {
		private final Name name;
		private final BirthDate birthDate;

		@JsonCreator
		public JsonPerson(
				@JsonProperty(value = "name", required = true) @JsonDeserialize(using = NameDeserializer.class) Name name, // thumbs up for custom deserializer!
				@JsonProperty(value = "birthDate", required = true) @JsonDeserialize(using = BirthDateDeserializer.class) BirthDate birthDate) {
			this.name = name;
			this.birthDate = birthDate;
		}

		@Override
		public String toString() {
			return "JsonPerson [" + name + ", =" + birthDate + "]";
		}

	}

	private static class NameDeserializer extends JsonDeserializer<Name> {
		@Override
		public Name deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
				throws IOException, JsonProcessingException {
			return new Name(jsonparser.getText());
		}
	}

	private static class BirthDateDeserializer extends JsonDeserializer<BirthDate> {
		@Override
		public BirthDate deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
				throws IOException, JsonProcessingException {
			return new BirthDate(LocalDate.parse(jsonparser.getText()));
		}
	}
}
