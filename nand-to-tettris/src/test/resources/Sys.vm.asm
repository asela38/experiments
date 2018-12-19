
// function Sys.init 0 
(Sys.init)

// push constant 4000 
	@4000 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop pointer 0 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@THIS // *sp = THIS
	M=D

// push constant 5000 
	@5000 // *sp = i
	D=A
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

// call Sys.main 0 

// call for Sys.main 
	@m$ret.0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@5
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Sys.main
	0;JMP
(m$ret.0)

// pop temp 1 
	@1 // addr = LCL + 1
	D=A
	@5
	D=A+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// label LOOP 
(LOOP)

// goto LOOP 
	@LOOP
	0;JMP

// function Sys.main 5 
(Sys.main)
	@0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@0
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push constant 4001 
	@4001 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop pointer 0 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@THIS // *sp = THIS
	M=D

// push constant 5001 
	@5001 // *sp = i
	D=A
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

// push constant 200 
	@200 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop local 1 
	@1 // addr = LCL + 1
	D=A
	@LCL
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

// push constant 40 
	@40 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop local 2 
	@2 // addr = LCL + 2
	D=A
	@LCL
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

// push constant 6 
	@6 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop local 3 
	@3 // addr = LCL + 3
	D=A
	@LCL
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

// push constant 123 
	@123 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// call Sys.add12 1 

// call for Sys.add12 
	@m$ret.1
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@LCL
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@ARG
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THIS
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@THAT
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1
	@6
	D=A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Sys.add12
	0;JMP
(m$ret.1)

// pop temp 0 
	@0 // addr = LCL + 0
	D=A
	@5
	D=A+D
	@addr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@addr
	A=M
	M=D

// push local 0 
	@0 // -- addr = LCL + 0
	D=A
	@LCL
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push local 1 
	@1 // -- addr = LCL + 1
	D=A
	@LCL
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push local 2 
	@2 // -- addr = LCL + 2
	D=A
	@LCL
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push local 3 
	@3 // -- addr = LCL + 3
	D=A
	@LCL
	A=D+M // *sp = *addr"
	D=M
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// push local 4 
	@4 // -- addr = LCL + 4
	D=A
	@LCL
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

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// add 
	@SP
	M=M-1
	A=M
	D=M
	A=A-1
	M=D+M

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP

// function Sys.add12 0 
(Sys.add12)

// push constant 4002 
	@4002 // *sp = i
	D=A
	@SP // Push D to Stack
	A=M
	M=D
	@SP // SP++
	M=M+1

// pop pointer 0 
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@THIS // *sp = THIS
	M=D

// push constant 5002 
	@5002 // *sp = i
	D=A
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

// push constant 12 
	@12 // *sp = i
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

// return 

// return 
	@LCL
	D=M
	@endFrame
	M=D
	@5
	A=D-A
	D=M
	@retAddr
	M=D
	@SP // *addr = *(SP--)
	M=M-1
	A=M
	D=M
	@ARG
	A=M
	M=D
	D=A+1
	@SP
	M=D
	@4
	D=A
	@endFrame
	A=M-D
	D=M
	@LCL
	M=D
	@3
	D=A
	@endFrame
	A=M-D
	D=M
	@ARG
	M=D
	@2
	D=A
	@endFrame
	A=M-D
	D=M
	@THIS
	M=D
	@1
	D=A
	@endFrame
	A=M-D
	D=M
	@THAT
	M=D
	@retAddr
	A=M
	0;JMP
