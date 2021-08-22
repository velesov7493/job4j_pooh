package ru.job4j.pooh.services;

import ru.job4j.pooh.models.Request;
import ru.job4j.pooh.models.Response;

public interface Service {

    Response process(Request request);
}
