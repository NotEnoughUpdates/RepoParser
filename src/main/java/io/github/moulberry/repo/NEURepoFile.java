package io.github.moulberry.repo;


import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class NEURepoFile {

    public String getPath() {
        return repository.baseFolder.relativize(fsPath).toString();
    }

    @Getter
    private NEURepository repository;
    @Getter
    private Path fsPath;

    public <T> T json(TypeToken<T> type) throws NEURepositoryException {
        try {
            return repository.gson.fromJson(Files.newBufferedReader(fsPath, StandardCharsets.UTF_8), type.getType());
        } catch (IOException | SecurityException e) {
            throw new NEURepositoryException(getPath(), "Could not read file", e);
        } catch (JsonSyntaxException e) {
            throw new NEURepositoryException(getPath(), "Invalid Json Syntax", e);
        }
    }

    public <T> T json(Class<T> $class) throws NEURepositoryException {
        try {
            return repository.gson.fromJson(Files.newBufferedReader(fsPath, StandardCharsets.UTF_8), $class);
        } catch (IOException | SecurityException e) {
            throw new NEURepositoryException(getPath(), "Could not read file", e);
        } catch (JsonSyntaxException e) {
            throw new NEURepositoryException(getPath(), "Invalid Json Syntax", e);
        }
    }

    public InputStream stream() throws NEURepositoryException {
        try {
            return Files.newInputStream(fsPath);
        } catch (IOException | SecurityException e) {
            throw new NEURepositoryException(getPath(), "Could not read file", e);
        }
    }

    public boolean isFile() {
        return Files.isRegularFile(fsPath);
    }
}
