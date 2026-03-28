function searchSongs() {
    const query = document.getElementById("searchBox").value;
    fetch("search?q=" + encodeURIComponent(query))
        .then(res => res.json())
        .then(data => {
            const resultsDiv = document.getElementById("results");
            resultsDiv.innerHTML = "";

            data.tracks.items.forEach(track => {
                const div = document.createElement("div");
                div.innerHTML = `
                    <p><b>${track.name}</b> - ${track.artists[0].name}</p>
                    <a href="${track.external_urls.spotify}" target="_blank">Play on Spotify</a>
                    <button onclick="favoriteSong('${track.id}', '${track.name}', '${track.artists[0].name}', '${track.external_urls.spotify}')">❤ Favorite</button>
                `;
                resultsDiv.appendChild(div);
            });
        })
        .catch(err => console.error(err));
}

function favoriteSong(trackId, trackName, artistName, url) {
    const formData = new URLSearchParams();
    formData.append("track_id", trackId);
    formData.append("track_name", trackName);
    formData.append("artist_name", artistName);
    formData.append("url", url);

    fetch("favorite", {
        method: "POST",
        body: formData
    })
    .then(res => res.text())
    .then(msg => alert(msg))
    .catch(err => console.error(err));
}
