package org.advert.report.repository;

import org.advert.report.bean.Machine;
import org.advert.report.util.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by shiqm on 2018-06-13.
 */

@Repository
public class MachineRepository extends MongoRepository<Machine,String>{

}
