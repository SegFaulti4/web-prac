package ru.msu.cmc.webprac.DAO;

import ru.msu.cmc.webprac.models.*;
import ru.msu.cmc.webprac.models.Thread;

public interface ThreadDAO extends CommonDAO<Thread, ThreadID> {

    Thread getByID(String part_name, String thread_name);

}