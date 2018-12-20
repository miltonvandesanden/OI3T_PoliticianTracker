var defaultURL = "http://localhost:8080/votingentities";
//var html = "";

$(document).ready
(
	() =>
	{
		let contentElements = document.getElementsByClassName("menuBar");
		contentElements[0].innerHTML = "<button onclick='loadContent(\"" + defaultURL + "\")' type='button'>" + "Politicians" + "</button>";
	}
	//loadContent(defaultURL)
);

function loadContent(url)
{
	fetch(url)
	.then(response => response.json())
	.then
	(
		content =>
		{
			let finalML = "";
			
			finalML += appendContent(url, content);
			
			let contentElements = document.getElementsByClassName("content");
			contentElements[0].innerHTML = finalML;
		}
	)
	.catch
	(
		error =>
		{
			console.error('An error ocurred', error);
		}
	)
}

function appendContent(url, property)
{
	let finalML = "<div class='object'>";
	
	Object.keys(property).forEach
	(
		(key, index) =>
		{
			if(typeof(property[key]) === "object")
			{
				finalML += "<div class='object'>";
				finalML += appendContent(url, property[key]);
				finalML += "</div>";
			}
			else if(key !== "id")
			{
				finalML += "<div class='property'>";
				
				if(key === "name")
				{
					finalML += "<b>" + property[key] + "</b>";
				}
				else if(key === "href")
				{
					//http://localhost:8080/votingentities
					if(property[key] === url)
					{
					finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Refresh" + "</button>";
					}
					else if(/\d/.test(property[key].substring(21)))
					{
						finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Go to" + "</button>";
					}
					else
					{
						finalML += "<button onclick='loadContent(\"" + property[key] + "\")' type='button'>" + "Back to All" + "</button>";
					}
				}
				else
				{
					finalML += key + ": " + property[key];
				}
				
				finalML += "</div>";
			}
		}
	);
	
	finalML += "</div>";
	return finalML;
}