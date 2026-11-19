package com.promtexmaster.PGPAKS.Backend;

import com.promtexmaster.PGPAKS.Backend.controller.MDAMController;
import com.promtexmaster.PGPAKS.Backend.controller.PTMController;
import com.promtexmaster.PGPAKS.Backend.model.mdam.Register;
import com.promtexmaster.PGPAKS.Backend.model.mdam.RegisterBatch;
import com.promtexmaster.PGPAKS.Backend.model.ptm.DBVersion;
import com.promtexmaster.PGPAKS.Backend.model.ptm.ParameterType;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Table;

import static org.junit.Assert.assertEquals;

public class NamingContractTest {

    @Test
    public void clientPathsStayOnTheExistingAddresses() {
        assertEquals("/pgpaks", MDAMController.class.getAnnotation(RequestMapping.class).path()[0]);
        assertEquals("/tct", PTMController.class.getAnnotation(RequestMapping.class).path()[0]);
    }

    @Test
    public void tablesStayInTheExistingSchemas() {
        assertEquals("tctdb11", DBVersion.class.getAnnotation(Table.class).schema());
        assertEquals("dbversion", DBVersion.class.getAnnotation(Table.class).name());
        assertEquals("tctdb11", ParameterType.class.getAnnotation(Table.class).schema());
        assertEquals("parametertypes", ParameterType.class.getAnnotation(Table.class).name());
        assertEquals("mdam_registers", Register.class.getAnnotation(Table.class).schema());
        assertEquals("register", Register.class.getAnnotation(Table.class).name());
        assertEquals("mdam_registers", RegisterBatch.class.getAnnotation(Table.class).schema());
        assertEquals("register_batch", RegisterBatch.class.getAnnotation(Table.class).name());
    }
}
