--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/3/12
-- Time: 13:32
-- To change this template use File | Settings | File Templates.
--

local num_pool_key = KEYS[1]
local telA = ARGV[1]
local telB = ARGV[2]
local bindid = ARGV[3]
local string_sub = string.sub
local insertKey='HW:X:BIND:'
local num_pool_len = redis.call('llen', num_pool_key)
-- redis.log(redis.LOG_DEBUG, "num_pool_len: " .. tostring(num_pool_len))
if num_pool_len ~= 0 then
    local startindex = redis.call('hincrBy', 'phonelockmap', num_pool_key, 1)
    local x_num = redis.call('lindex', num_pool_key, startindex % num_pool_len)
    local exist_xa = redis.call('hget',insertKey..string_sub(x_num, 1, 3)..':'..x_num,telA)
    local exist_xb = redis.call('hget',insertKey..string_sub(x_num, 1, 3)..':'..x_num,telB)
   if not exist_xa then
      if not exist_xb then
         redis.call('hset',insertKey..string_sub(x_num, 1, 3)..':'..x_num,telA,bindid)
         redis.call('hset',insertKey..string_sub(x_num, 1, 3)..':'..x_num,telB,bindid)
         return x_num
      end
   end
end