package com.qvik.events.infra.response.admindto;

import com.qvik.events.modules.restaurant.Restaurant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantADTO {

    private Restaurant restaurant = new Restaurant();

    private List<LinkToDTO> linkRestaurantCuisines = new ArrayList<>();

    private LinkToDTO linkRestaurantVenue = new LinkToDTO();

}
