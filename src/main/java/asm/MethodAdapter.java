package asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class MethodAdapter extends AdviceAdapter {

	protected MethodAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
		super(api, methodVisitor, access, name, descriptor);
	}

	@Override
	protected void onMethodExit(int opcode) {
		if (opcode != Opcodes.ATHROW) {
			mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mv.visitLdcInsn("just before return");
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "asmExample/VeryOldClass", "show2", "()V", false);
		}

		super.onMethodExit(opcode);
	}

	@Override
	protected void onMethodEnter() {
		mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitLdcInsn("just on enter");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
	}
}
