package org.example;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class ClashRoyale2 extends Mapper<Object, Text, LongWritable, Deck> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        try {
            // Convertir la ligne JSON en une chaîne de caractères
            String jsonRecord = value.toString();

            // Utiliser Jackson pour mapper la ligne JSON en un objet JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(jsonRecord);

            // Extraire les champs nécessaires de l'objet JsonNode
            String date = node.get("date").asText();
            String round = node.get("round").asText();
            int win = node.get("win").asInt();
            String cards = node.get("cards").asText().substring(0, 16); // Garder seulement les 8 premiers caractères
            String cards2 = node.get("cards2").asText().substring(0, 16); // Garder seulement les 8 premiers caractères
            int clanTr = node.has("clanTr") ? node.get("clanTr").asInt() : 0; // Mettre à 0 si absent
            int clanTr2 = node.has("clanTr2") ? node.get("clanTr2").asInt() : 0; // Mettre à 0 si absent


            // Vérifier si win, cards sont présents dans le deck
            if ((win == 0 || win == 1) && !cards.isEmpty() ) {
                // Créer un nouvel objet Deck avec les champs extraits
                Deck deck = new Deck(date, round, win, cards,cards2, clanTr, clanTr2);

                // Concaténer la date et le round pour former la clé
                String compositeKey = date + "_" + round;

                // Émettre la paire clé-valeur vers le reducer
                context.write(new LongWritable(compositeKey.hashCode()), deck);
            }
        } catch (Exception e) {
            // Gérer les exceptions si nécessaire
            e.printStackTrace();
        }
    }
}
/*
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JSONFilterReducer extends Reducer<LongWritable, Deck, NullWritable, Deck> {

    @Override
    protected void reduce(LongWritable key, Iterable<Deck> values, Context context) throws IOException, InterruptedException {
        // Utiliser un ensemble pour suivre les clés uniques
        Set<LongWritable> uniqueKeys = new HashSet<>();

        for (Deck deck : values) {
            // Ajouter la clé à l'ensemble pour vérifier les doublons
            if (uniqueKeys.add(new LongWritable(key.get()))) {
                // Émettre uniquement la première occurrence de la clé
                context.write(NullWritable.get(), deck);
            }
        }
    }
}
*/



