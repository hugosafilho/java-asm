package asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class AsmExample {

	private static final String CLASS_LOCATION = System.getProperty("user.dir") + File.separatorChar + "auxiliary"
			+ File.separatorChar + "asmExample" + File.separatorChar + "VeryOldClass.class";

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream(CLASS_LOCATION);

		ClassReader cr = new ClassReader(fis);

		/*
		 * Load class using COMPUTE_FRAMES to prevent headache when creating new methods
		 */
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

		cr.accept(new AsmClassVisitor(Opcodes.ASM4, cw), 0);

		FileOutputStream fos = new FileOutputStream(CLASS_LOCATION);
		fos.write(cw.toByteArray());
		fos.close();
	}
}
