package com.syswarp.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import com.syswarp.data.service.ContenedoresRepository;
import com.syswarp.data.entity.Contenedores;
import com.syswarp.data.service.MediosRepository;
import com.syswarp.data.entity.Medios;
import com.syswarp.data.service.MultiplicacionesRepository;
import com.syswarp.data.entity.Multiplicaciones;
import com.syswarp.data.service.OperariosRepository;
import com.syswarp.data.entity.Operarios;
import com.syswarp.data.service.VariedadesRepository;
import com.syswarp.data.entity.Variedades;
import com.syswarp.data.service.OperacionesRepository;
import com.syswarp.data.entity.Operaciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ContenedoresRepository contenedoresRepository, MediosRepository mediosRepository,
            MultiplicacionesRepository multiplicacionesRepository, OperariosRepository operariosRepository,
            VariedadesRepository variedadesRepository, OperacionesRepository operacionesRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (contenedoresRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            
            logger.info("... generating 100 Contenedores entities...");
            ExampleDataGenerator<Contenedores> contenedoresRepositoryGenerator = new ExampleDataGenerator<>(
                    Contenedores.class);
            contenedoresRepositoryGenerator.setData(Contenedores::setId, DataType.ID);
            contenedoresRepositoryGenerator.setData(Contenedores::setContenedor, DataType.WORD);
            contenedoresRepositoryGenerator.setData(Contenedores::setTagid, DataType.WORD);
            contenedoresRepository.saveAll(contenedoresRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Medios entities...");
            ExampleDataGenerator<Medios> mediosRepositoryGenerator = new ExampleDataGenerator<>(Medios.class);
            mediosRepositoryGenerator.setData(Medios::setId, DataType.ID);
           
            //mediosRepositoryGenerator.setData(Medios::setIdmedio, DataType.NUMBER_UP_TO_100);
            
            mediosRepositoryGenerator.setData(Medios::setMedio, DataType.WORD);
            mediosRepository.saveAll(mediosRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Multiplicaciones entities...");
            ExampleDataGenerator<Multiplicaciones> multiplicacionesRepositoryGenerator = new ExampleDataGenerator<>(
                    Multiplicaciones.class);
            multiplicacionesRepositoryGenerator.setData(Multiplicaciones::setId, DataType.ID);
           // multiplicacionesRepositoryGenerator.setData(Multiplicaciones::setIdmultiplicacion,
             //       DataType.NUMBER_UP_TO_100);
            multiplicacionesRepositoryGenerator.setData(Multiplicaciones::setIdmultiplicacion_padre,
                    DataType.NUMBER_UP_TO_100);
            multiplicacionesRepository.saveAll(multiplicacionesRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Operarios entities...");
            ExampleDataGenerator<Operarios> operariosRepositoryGenerator = new ExampleDataGenerator<>(Operarios.class);
            operariosRepositoryGenerator.setData(Operarios::setId, DataType.ID);
            operariosRepositoryGenerator.setData(Operarios::setOperario, DataType.WORD);
            operariosRepositoryGenerator.setData(Operarios::setUsuario, DataType.WORD);
            operariosRepositoryGenerator.setData(Operarios::setClave, DataType.WORD);
            operariosRepositoryGenerator.setData(Operarios::setEsadmin, DataType.WORD);
            operariosRepository.saveAll(operariosRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Variedades entities...");
            ExampleDataGenerator<Variedades> variedadesRepositoryGenerator = new ExampleDataGenerator<>(
                    Variedades.class);
            variedadesRepositoryGenerator.setData(Variedades::setId, DataType.ID);
           // variedadesRepositoryGenerator.setData(Variedades::setIdvariedad, DataType.NUMBER_UP_TO_100);
            variedadesRepositoryGenerator.setData(Variedades::setVariedad, DataType.WORD);
            variedadesRepository.saveAll(variedadesRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Operaciones entities...");
            ExampleDataGenerator<Operaciones> operacionesRepositoryGenerator = new ExampleDataGenerator<>(
                    Operaciones.class);
            operacionesRepositoryGenerator.setData(Operaciones::setId, DataType.ID);
          //  operacionesRepositoryGenerator.setData(Operaciones::setIdoperacion, DataType.NUMBER_UP_TO_100);
            operacionesRepositoryGenerator.setData(Operaciones::setFecha, DataType.DATE_OF_BIRTH);
          //  operacionesRepositoryGenerator.setData(Operaciones::setIdcontenedor, DataType.NUMBER_UP_TO_100);
            operacionesRepositoryGenerator.setData(Operaciones::setIdvariedad, DataType.NUMBER_UP_TO_100);
            operacionesRepositoryGenerator.setData(Operaciones::setIdoperario, DataType.NUMBER_UP_TO_100);
            operacionesRepositoryGenerator.setData(Operaciones::setIdmultiplicacion, DataType.NUMBER_UP_TO_100);
            operacionesRepositoryGenerator.setData(Operaciones::setIdmedio, DataType.NUMBER_UP_TO_100);
            operacionesRepositoryGenerator.setData(Operaciones::setObservaciones, DataType.WORD);
            operacionesRepository.saveAll(operacionesRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}