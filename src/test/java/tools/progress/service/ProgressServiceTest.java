package tools.progress.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import tools.progress.service.ProgressService;

@RunWith(MockitoJUnitRunner.class)
public class ProgressServiceTest {
	
	private ProgressService service = new ProgressService();

	@Test
	public void keepLine() {
		String toKeep = "fgzeg z {blabla1.i} {bla2bla.i} RUN bla2bla3srv.p gfzger";
		assertTrue( service.keepLine(toKeep) );

		String toNotKeep = " gzgzg zg zez .i gr eg er ger RUN grgrg44 ";
		assertFalse( service.keepLine(toNotKeep) );
	}

	@Test
	public void findDependencies() {
		String line1 = "fgzeg z {blabla1.i} {bla2bla.i} RUN bla2bla3srv.p gfzger";
		List<String> dependenciesToFind = Arrays.asList("blabla1.i", "bla2bla.i", "bla2bla3srv.p");
		List<String> dependenciesFind = service.findDependencies(line1);
		
		assertTrue(dependenciesFind.containsAll(dependenciesToFind));
		assertEquals(dependenciesToFind.size(), dependenciesFind.size());
	}


}
