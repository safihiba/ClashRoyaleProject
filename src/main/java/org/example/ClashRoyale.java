
package org.example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ClashRoyale {

    public class ClashRoyaleMapper extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            try {
                // Parse le JSON et extrait les champs Date et round
                String jsonInput = value.toString();
                String[] fields = jsonInput.split(",");

                String date = extractField(fields, "date");
                String round = extractField(fields, "round");

                // Concatène Date et round pour créer une clé unique
                String uniqueKey = date + "_" + round;

                // Effectue le data cleaning en supprimant les données invalides
                if (isValidData(fields)) {
                    // Émet la clé et les données nettoyées pour le traitement ultérieur
                    context.write(new Text(uniqueKey), new Text(jsonInput));
                }
            } catch (Exception e) {
                // Gérer les erreurs de parsing ou toute exception
                // Vous pouvez également logger l'erreur si nécessaire
            }
        }

        private String extractField(String[] fields, String fieldName) {
            for (String field : fields) {
                if (field.contains("\"" + fieldName + "\"")) {
                    // Trouver la première occurrence du deux-points à partir de la sous-chaîne "fieldName"
                    int colonIndex = field.indexOf(":", field.indexOf("\"" + fieldName + "\""));

                    // Si un deux-points est trouvé
                    if (colonIndex != -1) {
                        // Extraire la sous-chaîne à partir de l'indice après le deux-points jusqu'à la fin
                        String valuePart = field.substring(colonIndex + 1);

                        // Supprimer les guillemets doubles et nettoyer la valeur
                        return valuePart.replaceAll("\"", "").trim();
                    }
                }
            }
            return "";
        }


        private boolean isValidData(String[] fields) {
            // Vérifiez les conditions pour déterminer si les données sont valides
            // Vous pouvez ajouter des vérifications supplémentaires selon vos besoins
            return fields.length > 0 && fields[0].contains("\"date\"") && fields[0].contains("\"round\"");
        }
    }

    public static class ClashRoyaleReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            try {
                // Effectue l'analyse des combinaisons de cartes et génère les résultats
                List<String> uniqueDecks = new ArrayList<String>();

                for (Text value : values) {
                    // Ajoute chaque deck unique à la liste
                    String jsonInput = value.toString();
                    if (!uniqueDecks.contains(jsonInput)) {
                        uniqueDecks.add(jsonInput);
                    }
                }

                // Exemple d'analyse générique (compter le nombre total de decks uniques)
                int totalUniqueDecks = uniqueDecks.size();

                // Vous pouvez maintenant effectuer votre analyse spécifique sur la liste uniqueDecks
                // et générer les résultats souhaités

                // Émet la clé et les résultats pour le traitement ultérieur
                context.write(key, new Text("Nombre total de decks uniques : " + totalUniqueDecks));
            } catch (Exception e) {
                // Gérer les erreurs ou exceptions
                // Vous pouvez ajouter votre propre logique de gestion des erreurs ici
                // Par exemple, logger l'erreur ou envoyer une notification
                context.write(key, new Text("Erreur pendant l'analyse : " + e.getMessage()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "ClashRoyale");

        job.setJarByClass(ClashRoyale.class);
        job.setMapperClass(ClashRoyaleMapper.class);
        job.setReducerClass(ClashRoyaleReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}



