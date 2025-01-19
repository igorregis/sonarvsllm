    hash_order_a = (a & 0xff) << 24 | ((a >> 8) & 0xff) << 16 | ((a >> 16) & 0xff) << 8 | ((a >> 24) & 0xff);
    // Endian issues...
    //hash_order_a = a;
    // Adjust search_high & search_low to work through space
    if (hash_order_mem < hash_order_a) {
      search_low = search_index + 1;
    } else  {
      search_high = search_index;
    }
    if ((hash_order_a == hash_order_mem) && (search_low < numberOfPasswords)) {
      // Break out of the search loop - search_index is on a value
      break;
    }
  }

  // Yes - it's a goto.  And it speeds up performance significantly (15%).
  // It stays.  These values are already loaded.  If they are not the same,
  // there is NO point to touching global memory again.
  if (hash_order_a != hash_order_mem) {
    goto next;
  }
  // We've broken out of the loop, search_index should be on a matching value
  // Loop while the search index is the same - linear search through this to find all possible
  // matching passwords.
  // We first need to move backwards to the beginning, as we may be in the middle of a set of matching hashes.
  // If we are index 0, do NOT subtract, as we will wrap and this goes poorly.

  while (search_index && (a == DEVICE_Hashes_32[(search_index - 1) * 2])) {
    search_index--;
  }
