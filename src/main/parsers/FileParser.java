package main.parsers;

public interface FileParser<T> {
    T Parse(String path);
}
