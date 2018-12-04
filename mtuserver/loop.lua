-- This is a Lua file
local a = {}
function test()
	print("Hello world! Lets count")
	local x = 0
	while(true) do
		a[x] = x
		x += 1
		print(x)
	end

end
