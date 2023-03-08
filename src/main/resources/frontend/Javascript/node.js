function filterProducts() {
  const priceRangeFilter = document.getElementById("price-range-filter").value;
  const sortFilter = document.getElementById("sort-filter").value;

  const products = document.querySelectorAll(".product-name");

  // Reset to original products if "relevance" is selected
  /*if (sortFilter === "relevance") {
    const sortedProducts = originalProducts.sort((a, b) => {
      const aName = a.querySelector("h3").textContent.toLowerCase();
      const bName = b.querySelector("h3").textContent.toLowerCase();
      return aName.localeCompare(bName);
    });

    for (let i = 0; i < sortedProducts.length; i++) {
      sortedProducts[i].parentNode.appendChild(sortedProducts[i]);
    }

    return;
  }*/

  if (sortFilter === "relevance") {
    location.reload();
  }

  for (let i = 0; i < products.length; i++) {
    const productPrice = parseFloat(products[i].querySelector(".product-price span").textContent.slice(1));

    // filter by price range
    if (priceRangeFilter) {
      const [minPrice, maxPrice] = priceRangeFilter.split("-");
      if (productPrice < parseFloat(minPrice) || productPrice > parseFloat(maxPrice)) {
        products[i].style.display = "none";
        continue;
      }
    }

    products[i].style.display = "block";
  }

  // sort products
  if (sortFilter === "price-low-high") {
    const sortedProducts = Array.from(products).sort((a, b) => {
      const priceA = parseFloat(a.querySelector(".product-price span").textContent.slice(1));
      const priceB = parseFloat(b.querySelector(".product-price span").textContent.slice(1));
      return priceA - priceB;
    });

    for (let i = 0; i < sortedProducts.length; i++) {
      sortedProducts[i].parentNode.appendChild(sortedProducts[i]);
    }
  } else if (sortFilter === "price-high-low") {
    const sortedProducts = Array.from(products).sort((a, b) => {
      const priceA = parseFloat(a.querySelector(".product-price span").textContent.slice(1));
      const priceB = parseFloat(b.querySelector(".product-price span").textContent.slice(1));
      return priceB - priceA;
    });

    for (let i = 0; i < sortedProducts.length; i++) {
      sortedProducts[i].parentNode.appendChild(sortedProducts[i]);
    }
  }
}
