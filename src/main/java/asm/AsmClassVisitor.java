package asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmClassVisitor extends ClassVisitor {

	private static final String METHOD_NAME = "show";

	protected AsmClassVisitor(int api, ClassVisitor classVisitor) {
		super(api, classVisitor);
	}

	@Override
	public void visitEnd() {
		MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "show2", "()V", null, null);

		mv.visitCode();

		mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitLdcInsn("show2");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitEnd();

		/*
		 * As I COMPUTE_FRAMES when loading class it is not necessary to worry about
		 * maxStack and maxLoad everything is calculated automatically. So you can use
		 * any values.
		 *
		 */
		mv.visitMaxs(0, 0);

		super.visitEnd();
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
			String[] exceptions) {

		if (METHOD_NAME.equals(name)) {
			MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);
			methodVisitor = new MethodAdapter(Opcodes.ASM4, methodVisitor, access, name, descriptor);

			return methodVisitor;
		}

		return super.visitMethod(access, name, descriptor, signature, exceptions);
	}
}
