local num_key = KEYS[1]
local telB = ARGV[1]
local area=redis.call('hget', num_key, telB)
if area then
   return area
end