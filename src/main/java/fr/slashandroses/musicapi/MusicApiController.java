package fr.slashandroses.musicapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import fr.slashandroses.musicapi.model.Song;
import fr.slashandroses.musicapi.model.Songs;
import lombok.extern.slf4j.Slf4j;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by snr on 19/10/2017.
 */

@RestController
@Slf4j
public class MusicApiController {

    @Value("${url}")
    private String url;

    @Value("${api}")
    private String api;

    @Value("${key}")
    private String apiKey;

    @RequestMapping("/")

    public String musicListing(@RequestHeader(value = "artist", defaultValue = "") String artist, @RequestHeader(value = "path", defaultValue = "C:\\xampp\\htdocs\\music") String filepath) throws JsonProcessingException, IOException, InvalidDataException, UnsupportedTagException {
        File f = new File(filepath);
        StringBuilder builder = new StringBuilder();
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));

        ObjectMapper mapper = new ObjectMapper();

        int cursor = 0;
        Songs songs = new Songs();
        ArrayList<Song> songsList = new ArrayList<>();
        for (File file : files) {
            Song song = new Song();
            Mp3File mp3 = new Mp3File(file);

            song.setTrack(mp3.getId3v2Tag().getTitle());
            song.setArtist(mp3.getId3v2Tag().getArtist());
            song.setAlbum(mp3.getId3v2Tag().getAlbum());
            song.setTrackNb(mp3.getId3v2Tag().getTrack());
            song.setTag(mp3.getId3v2Tag().getGenreDescription());
            song.setUrl(url + names.get(cursor));
            song.setYear(mp3.getId3v2Tag().getYear());
            songsList.add(song);
            cursor++;
        }
        songs.setSongs(songsList);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(songs) + artist;
    }

    @RequestMapping("/cover")
    public String coverGetter(@RequestHeader(value = "artist", defaultValue = "") String artist, @RequestHeader(value = "album", defaultValue = "") String album, @RequestHeader(value = "path", defaultValue = "C:\\xampp\\htdocs\\music") String filepath) throws JsonProcessingException, IOException, InvalidDataException, UnsupportedTagException {

        NodeList list = null;
        try {
            Parser parser = new Parser(String.format(api, apiKey, artist, album));
            list = parser.parse(null);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        gson.fromJson(list.toHtml(), String.class);
        return "lol";
    }


}
