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
			List<JsonPerson> loadedJsonPerson = Arrays.asList(mapper.readValue(jsonParser, JsonPerson[].class));
			return loadedJsonPerson.stream().map(p -> new Person(p.name, p.birthDate)).collect(Collectors.toList())
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
	private static class JsonPerson {
		private final Name name;
		private final BirthDate birthDate;

		@JsonCreator
		public JsonPerson(
				@JsonProperty(value = "name", required = true) @JsonDeserialize(using = NameDeserializer.class) Name name,
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
