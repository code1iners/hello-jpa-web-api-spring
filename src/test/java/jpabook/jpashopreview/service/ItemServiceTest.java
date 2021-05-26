package jpabook.jpashopreview.service;

import jpabook.jpashopreview.domain.item.Album;
import jpabook.jpashopreview.domain.item.Item;
import jpabook.jpashopreview.domain.item.Movie;
import jpabook.jpashopreview.service.item.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    public void saveItem() throws Exception {
        // given
        Album album = new Album();
        album.setPrice(10000);

        Movie movie = new Movie();
        movie.setPrice(15000);

        // when
        Long savedAlbumId = itemService.saveItem(album);
        Long savedMovieId = itemService.saveItem(movie);

        Item foundAlbum = itemService.findItem(savedAlbumId);
        Item foundMovie = itemService.findItem(savedMovieId);
        List<Item> foundAll = itemService.findItems();

        // then
        assertThat(album).isEqualTo(foundAlbum);
        assertThat(foundAlbum.getPrice()).isEqualTo(10000);
        assertThat(movie.getPrice()).isEqualTo(foundMovie.getPrice());
        assertThat(foundAll.size()).isEqualTo(2);
    }

}