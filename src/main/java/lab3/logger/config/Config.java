package lab3.logger.config;

import lab3.logger.AppenderLevel;
import lab3.logger.append.ConsolAppender;
import lab3.logger.append.DataBaseAppender;
import lab3.logger.append.FileAppender;
import lab3.logger.filter.ClassFilter;
import lab3.logger.filter.ExceptionTextFilter;
import lab3.logger.filter.LevelFilter;
import lab3.logger.filter.MessageTextFilter;
import lab3.logger.layout.Layout;
import lab3.logger.level.Level;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для задания и хранения конфигураций логера
 * @author Кирилл
 * @version 1.0
 */
@XmlRootElement(name = "Configurations")
public class Config {

    /**
     * Коллекция для хранения конфигураций логгера
     */
    private List<Configuration> configurations = new ArrayList<>();

    /**
     * Конструктор по-умолчанию, для рефлексивного создания объекта после анмаршлинга
     */
    public Config() {}

    /**
     * Геттер коллекции с конфигурациями логера
     * @return configurations - коллекция с конфигурациями логгера
     */
    public List<Configuration> getConfigurations() {
        return configurations;
    }

    /**
     * Сеттер коллекции с конфигурациями логера
     * @param configurations - коллекция с конфигурациями логгера
     */
    @XmlElement(name = "Configuration")
    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    /**
     * Метод для поулчения конфигураций логера, заданных через Java код
     * @return this - объект содержащий конфигурации логера
     */
    public Config readConfig() throws IOException {
        String category1 = "lab3.logger.main";

        String category3 = "lab3.logger.Test";

        List<AppenderLevel> appenderLevelList3 = new ArrayList<>();

        List<AppenderLevel> appenderLevelList1 = new ArrayList<>();

        AppenderLevel levApp1 = new AppenderLevel(
                Level.WARN,
                    new ConsolAppender(new Layout("%p %d{H:m:s,Y.M.D} %c %m %t %s", "|"),
                            new MessageTextFilter("123"),
                            new LevelFilter("ERROR"),
                            new ExceptionTextFilter("NullPointerException")),
                    new FileAppender("log.txt",
                            new Layout("%p %d{H:m:s,Y.M.D} %c %m %t %s", "|"),
                            new MessageTextFilter("System")));

        AppenderLevel levApp3 = new AppenderLevel(
                Level.DEBUG,
                new ConsolAppender(new Layout("%p %d{H:m:s,Y.M.D} %c %t %m", "|"),
                        new MessageTextFilter("message")));

        AppenderLevel levApp2 = new AppenderLevel(
                Level.INFO,
                    new DataBaseAppender("jdbc:mysql://localhost:3306/mydatabase", "kirill", "123456",
                            "com.mysql.jdbc.Driver",
                            "INSERT INTO log_table(date, level, class, thread, message, stacktrace) VALUES(?, ?, ?, ?, ?, ?)",
                            new Layout("%d{H:m:s,Y.M.D} %p %c %t %m %s")));

        //AppenderLevel levApp3 = new AppenderLevel(Level.TRACE, new DataBaseAppender("jdbc:mysql://127.0.0.1:3306/mydatabase", "kirill", "123456", new Layout("%p %d{H:m:s,Y.M.D} %c %m")));

        appenderLevelList1.add(levApp1);
        //appenderLevelList1.add(levApp2);
        //appenderLevelList1.add(levApp3);

        appenderLevelList3.add(levApp3);

        Configuration c1 = new Configuration(category1, appenderLevelList1);
        configurations.add(c1);

        Configuration c3 = new Configuration(category3, appenderLevelList3);
        configurations.add(c3);

        return this;
    }
}
