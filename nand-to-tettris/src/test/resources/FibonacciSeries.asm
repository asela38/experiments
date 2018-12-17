
// push argument 1 
	@1 // -- addr = LCL + 1
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop pointer 1 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@THAT // *sp = THAT
	M=D

// push constant 0 
	@0 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop that 0 
	@0 // addr = LCL + 0
	D=A
	@THAT
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// push constant 1 
	@1 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop that 1 
	@1 // addr = LCL + 1
	D=A
	@THAT
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 2 
	@2 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// sub 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D-M
	M=-M

// pop argument 0 
	@0 // addr = LCL + 0
	D=A
	@ARG
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// label MAIN_LOOP_START 
(MAIN_LOOP_START)
// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// if-goto COMPUTE_ELEMENT 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@COMPUTE_ELEMENT
	D;JNE

// goto END_PROGRAM 
	@END_PROGRAM
	0;JMP

// label COMPUTE_ELEMENT 
(COMPUTE_ELEMENT)
// push that 0 
	@0 // -- addr = LCL + 0
	D=A
	@THAT
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push that 1 
	@1 // -- addr = LCL + 1
	D=A
	@THAT
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// pop that 2 
	@2 // addr = LCL + 2
	D=A
	@THAT
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// push pointer 1 
	@THAT // *sp = THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 1 
	@1 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// pop pointer 1 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@THAT // *sp = THAT
	M=D

// push argument 0 
	@0 // -- addr = LCL + 0
	D=A
	@ARG
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 1 
	@1 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// sub 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D-M
	M=-M

// pop argument 0 
	@0 // addr = LCL + 0
	D=A
	@ARG
	D=M+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// goto MAIN_LOOP_START 
	@MAIN_LOOP_START
	0;JMP

// label END_PROGRAM 
(END_PROGRAM)